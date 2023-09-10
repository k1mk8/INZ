for date in range(1, 3):
    for hour in range(6, 14):
        for employee in range(1, 14, 2):
            print(f'''insert into schedule ("datetime", employee_id, is_occupied) values ('2023-08-{date} {hour}:00', {employee}, 0);''')
    for hour in range(14, 22):
        for employee in range(2, 13, 2):
            print(f'''insert into schedule ("datetime", employee_id, is_occupied) values ('2023-08-{date} {hour}:00', {employee}, 0);''')