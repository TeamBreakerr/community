CREATE TABLE notification
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    gmt_create BIGINT NOT NULL,
    status INT DEFAULT 0 NOT NULL,
    notifier INT NOT NULL,
    notifier_name VARCHAR(100) NULL,
    receiver INT NOT NULL,
    outerId INT NOT NULL,
    outer_title VARCHAR(256) NULL,
    type INT NOT NULL
);