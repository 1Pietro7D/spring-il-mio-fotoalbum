INSERT INTO albumfotografico.photo(title, description, tag, url, visible) VALUES('Monte Bianco', "la montagna più bianca", "montagna", 'https://picsum.photos/id/29/200/150', true);
INSERT INTO albumfotografico.photo(title, description, tag, url, visible) VALUES('Zozzomarina', 'il mare pulito', "mare", 'https://picsum.photos/id/16/200/150', true);
INSERT INTO albumfotografico.photo(title, description, tag, url, visible) VALUES('New York', 'la grande mela', "metropoly", 'https://picsum.photos/id/43/200/150', true);
INSERT INTO albumfotografico.photo(title, description, tag, url, visible) VALUES('Grecia', 'grecia', "mare", 'https://picsum.photos/id/49/200/150', true);
INSERT INTO albumfotografico.category(name) VALUES('viaggiare');
INSERT INTO albumfotografico.category(name) VALUES('città');
INSERT INTO albumfotografico.category(name) VALUES('moda');
INSERT INTO albumfotografico.category(name) VALUES('tecnologia');
INSERT INTO albumfotografico.category_photo(photo_id, category_id) VALUES(1,1);
INSERT INTO albumfotografico.category_photo(photo_id, category_id) VALUES(4,1);
INSERT INTO albumfotografico.category_photo(photo_id, category_id) VALUES(2,1);
INSERT INTO albumfotografico.category_photo(photo_id, category_id) VALUES(3,2);
INSERT INTO albumfotografico.category_photo(photo_id, category_id) VALUES(3,3);
INSERT INTO albumfotografico.user(id, username, password) VALUES(1,'admin','{bcrypt}$2a$10$4zuKJIl.sBQswhNNxSKSTO2nyLcuVRaLf4iBX4R4/s2mFzE5kCxLu'); -- pass : miao
INSERT INTO albumfotografico.user(id, username, password) VALUES(2,'user','{bcrypt}$2a$10$4zuKJIl.sBQswhNNxSKSTO2nyLcuVRaLf4iBX4R4/s2mFzE5kCxLu'); -- pass : miao
INSERT INTO albumfotografico.role(id, name) VALUES(1,'ADMIN');
INSERT INTO albumfotografico.role(id, name) VALUES(2, 'USER');
INSERT INTO albumfotografico.user_roles(user_id, roles_id) VALUES(1,1);
INSERT INTO albumfotografico.user_roles(user_id, roles_id) VALUES(2,2);
