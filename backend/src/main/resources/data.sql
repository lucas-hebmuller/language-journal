INSERT INTO skills (name) VALUES
  ('Listening'),
  ('Speaking'),
  ('Reading'),
  ('Writing'),
  ('Vocabulary'),
  ('Grammar')
ON CONFLICT DO NOTHING;