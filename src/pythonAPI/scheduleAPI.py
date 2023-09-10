import psycopg2

class API():
      def __init__(self, product_name: str) -> None:
            self.conn = self.connect()
            self.cursor = self.conn.cursor()
            self.product_name = product_name
            self.product_id = None
            self.professions_needed_time = None
            self.queries = [None, None, None, None, None, None]
      
      def connect(self):
            conn = psycopg2.connect(
                  database="postgres", user='postgres', password='postgres', host='127.0.0.1', port= '5432'
            )
            type(conn)
            return conn

      def commit(self) -> None:
            self.conn.commit()

      def disconnect(self) -> None:
            self.conn.close()

      def getHoursForEmployees(self) -> None:
            self.product_id = self.getProductId(self.product_name)
            self.professions_needed_time = self.getNeededProfession(self.product_id)
            self.getLastHourOfTasks(self.professions_needed_time)

      def getProductId(self, product_name: str) -> int:
            self.cursor.execute("""
            SELECT id FROM PRODUCT
            WHERE name = %s
            """, [product_name])
            product_id = self.cursor.fetchone()[0]
            return(product_id)

      def getNeededProfession(self, product_id: int) -> list:
            self.cursor.execute("""
            SELECT Profession, Time_needed FROM PRODUCT_COMPONENT
            WHERE %s = Product_id
            """, [product_id])
            professions_time = self.cursor.fetchall()
            return professions_time

      def getLastHourOfTasks(self, professions_needed_time: list) -> list:
            last_time_of_schedule = [None, None, None, None, None, "01-01-1999 00:00"]
            reference_of_professions = [5, 5, 1, 2, 3, 4]

            idx = 0
            for profession, time in professions_needed_time:
                  # drivers cannot finish their work until it is done
                  # (when they drive they cannot come back with the cargo)
                  if profession != "kierowca":
                        self.cursor.execute("""   
                        SELECT DISTINCT ON (datetime) datetime, EMPLOYEE.id FROM SCHEDULE
                        INNER JOIN EMPLOYEE ON Employee_id = EMPLOYEE.id
                        WHERE profession = %s AND Is_occupied = '0'
                        AND datetime > %s
                        GROUP BY EMPLOYEE.id, datetime
                        ORDER BY datetime LIMIT %s
                        """, [profession, last_time_of_schedule[reference_of_professions[idx]], time])
                  else:
                        # If the delivery is longer than the single shift it can be longer than a day 
                        self.cursor.execute("""
                        SELECT DATE(a.datetime) AS date, a.id FROM
                        (
                              SELECT DISTINCT ON (datetime) datetime, EMPLOYEE.id FROM SCHEDULE
                              INNER JOIN EMPLOYEE ON Employee_id = EMPLOYEE.id
                              WHERE profession = %s AND Is_occupied = '0'
                              AND datetime > %s
                              GROUP BY EMPLOYEE.id, datetime
                              ORDER BY datetime
                        ) a
                        GROUP BY date, a.id
                        HAVING COUNT(a.datetime) >= %s
                        ORDER BY date
                        """, [profession, last_time_of_schedule[reference_of_professions[idx]], time])
                        schedule = self.cursor.fetchone()
                        self.cursor.execute(
                        """
                              SELECT DISTINCT ON (datetime) datetime, EMPLOYEE.id FROM SCHEDULE
                              INNER JOIN EMPLOYEE ON Employee_id = EMPLOYEE.id
                              WHERE employee.id = %s AND Is_occupied = '0'
                              AND DATE(datetime) = %s
                              GROUP BY EMPLOYEE.id, datetime
                              ORDER BY datetime LIMIT %s
                        """, [schedule[1], schedule[0], time])
                  schedule = self.cursor.fetchall()
                  self.queries[idx] = schedule
                  last_time_of_schedule[idx] = schedule[-1][0]
                  idx += 1
            return(last_time_of_schedule)

      def setHoursForEmployees(self) -> None:
            for query in self.queries:
                  for task in query:
                        timestamp = task[0]
                        employee_id = task[1]
                        self.cursor.execute("""
                        UPDATE SCHEDULE
                        SET Is_occupied = 1
                        WHERE datetime = %s AND employee_id = %s
                        """, [timestamp, employee_id])

      def getNeededMaterials(self, product_id: int) -> list:
            self.cursor.execute("""
            SELECT id, amount FROM GOODS
            WHERE %s = product_id
            """, [product_id])
            goods = self.cursor.fetchall()
            return(goods)

      def __del__(self) -> None:
            self.disconnect()

api = API("Knz benetto")
# print(api.getNeededMaterials(1))
# api.getHoursForEmployees()
# api.setHoursForEmployees()
# api.commit()

# Przyjmij zamówienie
# Zamów towar
# Zajmij sloty
# wygeneruj zamówienia dokumenty etc
# status na w realizacji

# status wysłano
# zajmij 1 kierowce na 1 dzień
