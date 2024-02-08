
DROP TABLE IF EXISTS enrollments;
DROP TABLE IF EXISTS courses;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
	user_id INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(50),
	email VARCHAR(100) UNIQUE NOT NULL,
	password VARCHAR(100),
	user_type ENUM('STUDENT', 'TEACHER') NOT NULL
);

CREATE TABLE courses (
    course_id INT PRIMARY KEY AUTO_INCREMENT,
    course_name VARCHAR(50) NOT NULL,
    teacher_id INT NOT NULL,
    FOREIGN KEY (teacher_id) REFERENCES users(user_id)
);

CREATE TABLE enrollments (
    enrollment_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    course_id INT,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (course_id) REFERENCES courses(course_id),
    UNIQUE(user_id, course_id)  -- Ensures a user can't enroll in the same course multiple times
);


INSERT INTO users (user_type,name,email,password) VALUES ('TEACHER','Devin', 'dcat@macchiato.life', 'KJ9jhfa9fa9');

SELECT * FROM users;

SELECT * FROM courses;

SELECT * FROM enrollments;