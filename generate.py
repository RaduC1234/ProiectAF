from faker import Faker
import random

fake = Faker()

def generate_fake_data(num_records):
    data = []
    for _ in range(num_records):
        record = {
            "id": fake.random_number(digits=5),
            "age": random.randint(18, 99),
            "email": fake.email(),
            "firstName": fake.first_name(),
            "lastName": fake.last_name()
        }
        data.append(record)
    return data

def generate_insert_commands(table_name, data):
    insert_commands = []
    for record in data:
        columns = ', '.join(record.keys())
        values = ', '.join([f"'{value}'" if isinstance(value, str) else str(value) for value in record.values()])
        insert_commands.append(f"INSERT INTO {table_name} ({columns}) VALUES ({values});")
    return insert_commands

def write_to_file(file_path, content):
    with open(file_path, 'w') as file:
        file.write(content)

table_name = "users"

num_records = 100
fake_data = generate_fake_data(num_records)

insert_commands = generate_insert_commands(table_name, fake_data)

file_path = "insert_commands.sql"
write_to_file(file_path, '\n'.join(insert_commands))

print(f"SQL insert commands written to {file_path}")
