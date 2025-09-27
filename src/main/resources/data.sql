INSERT INTO group_categories (name, active, price) VALUES
                                                       ('Ясельная группа', true, 5000),
                                                       ('Младшая группа', true, 4500);

INSERT INTO teachers (first_name, last_name, patronymic, active, teacher_degree) VALUES
                                                                                     ('Айгуль', 'Ибраимова', 'Канатбековна', true, 'TEACHER'),
                                                                                     ('Гульнара', 'Садыкова', 'Руслановна', true, 'NANNY');

INSERT INTO groups (name, max_children_count, price, nanny_id, group_category_id, teacher_id) VALUES
                                                                                                  ('Солнышко', 15, 5000, 2, 1, 1),
                                                                                                  ('Радуга', 18, 4500, 2, 2, 1);


INSERT INTO childrens (first_name, last_name, patronymic, date_of_birth) VALUES
                                                                             ('Айлин', 'Алиева', 'Руслановна', '2022-03-15'),
                                                                             ('Амир', 'Касымов', 'Нурланович', '2021-02-14');

INSERT INTO group_childrens (start_date, end_date, price, children_id, group_id) VALUES
                                                                                     ('2024-01-10', NULL, 5000, 1, 1),  -- активен в группе
                                                                                     ('2024-01-15', '2024-02-01', 4500, 2, 2);  -- отчислен

INSERT INTO payments (amount, payment_date, group_children_id) VALUES
                                                                   (5000, '2024-01-05', 1),
                                                                   (4500, '2024-01-20', 2);