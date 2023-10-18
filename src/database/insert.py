for date in range(1, 10):
    for hour in range(6, 14):
        for employee in range(1, 14, 2):
            print(f'''insert into schedule ("datetime", employee_id) values ('2023-11-{date} {hour}:00', {employee});''')
    for hour in range(14, 22):
        for employee in range(2, 13, 2):
            print(f'''insert into schedule ("datetime", employee_id) values ('2023-11-{date} {hour}:00', {employee});''')