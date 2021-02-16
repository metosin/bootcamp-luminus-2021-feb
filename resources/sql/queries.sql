-- :name sql-create-user! :<!
-- :doc creates a new user record
INSERT INTO users
(first_name, last_name, email, pass)
VALUES (:first_name, :last_name, :email, :pass)
RETURNING id, first_name, last_name, email;

-- :name sql-update-user! :! :n
-- :doc updates an existing user record
UPDATE users
SET first_name = :first_name, last_name = :last_name, email = :email
WHERE id = :id;

-- :name sql-get-user :? :1
-- :doc retrieves a user record given the id
SELECT id, first_name, last_name, email FROM users
WHERE id = :id;

-- :name sql-get-users :? :*
-- :doc retrieves all users
SELECT * FROM users;

-- :name sql-delete-user! :! :n
-- :doc deletes a user record given the id
DELETE FROM users
WHERE id = :id;
