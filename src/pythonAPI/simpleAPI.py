import psycopg2

#establishing the connection
conn = psycopg2.connect(
   database="postgres", user='postgres', password='postgres', host='127.0.0.1', port= '5432'
)
#Creating a cursor object using the cursor() method
cursor = conn.cursor()


cursor.execute("""
SELECT * FROM SUPPLIER
""")

result = cursor.fetchone()
print(result)


# #Executing an MYSQL function using the execute() method
# cursor.execute("""INSERT INTO SUPPLIER(id, NAME) 
#    VALUES (1, 'Mac')""")

# # Deleting
# cursor.execute("""DELETE FROM SUPPLIER""")

# # Fetch a single row using fetchone() method.
# data = cursor.fetchone()
# print("Connection established to: ",data)

# conn.commit()

#Closing the connection
conn.close()