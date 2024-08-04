use AIAnswering;

-- User Table Initial Data
INSERT INTO user (id, userAccount, userPassword, unionId, mpOpenId, userName, userAvatar, userProfile, userRole,
                  createTime, updateTime, isDelete)
VALUES (1, 'steven', 'b3c977db3a04a3e59fda655761e15ac2', null, null, 'Steven',
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSz6yp9nhKO-3OHuRj29--0kvWMVvz2y87NWA&s',
        'My name is Xiaozhang Cui', 'admin', '2024-05-09 11:13:13', '2024-05-09 15:07:48', 0);

-- Application Table Initial Data
INSERT INTO app (id, appName, appDesc, appIcon, appType, scoringStrategy, reviewStatus, reviewMessage, reviewerId,
                 reviewTime, userId, createTime, updateTime, isDelete)
VALUES (1, 'Customised MBTI Personality Test', 'Personality Test', 'https://img1.baidu.com/it/u=3862985929,50797134&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=503', 1, 1, 1, null, null, null, 1, '2024-04-24 15:58:05', '2024-05-09 15:09:53', 0);
INSERT INTO app (id, appName, appDesc, appIcon, appType, scoringStrategy, reviewStatus, reviewMessage, reviewerId,
                 reviewTime, userId, createTime, updateTime, isDelete)
VALUES (2, 'Custom Score Test', 'Test scores', 'https://img1.baidu.com/it/u=1081983487,3088109403&fm=253&fmt=auto&app=138&f=JPEG?w=499&h=333', 0, 0, 1, null, null, null, 1, '2024-04-25 11:39:30', '2024-05-09 15:09:53', 0);
INSERT INTO app (id, appName, appDesc, appIcon, appType, scoringStrategy, reviewStatus, reviewMessage, reviewerId,
                 reviewTime, userId, createTime, updateTime, isDelete)
VALUES (3, 'AI MBTI Personality Test', 'Get your MBTI tested', 'https://img1.baidu.com/it/u=3862985929,50797134&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=503', 1, 1, 1, null, null, null, 1, '2024-04-26 16:38:12',
        '2024-05-09 15:09:53', 0);
INSERT INTO app (id, appName, appDesc, appIcon, appType, scoringStrategy, reviewStatus, reviewMessage, reviewerId,
                 reviewTime, userId, createTime, updateTime, isDelete)
VALUES (4, 'AI Score Test', 'Let''s see how many capitals you know', 'https://img1.baidu.com/it/u=1081983487,3088109403&fm=253&fmt=auto&app=138&f=JPEG?w=499&h=333', 0, 1, 1, null, null, null, 1, '2024-04-26 16:38:56', '2024-05-09 15:09:53', 0);

-- Initial data for the questionnaire
INSERT INTO question (id, questionContent, appId, userId, createTime, updateTime, isDelete)
VALUES (1,
        '[{"options":[{"result":"I","value":"Work alone","key":"A"},{"result":"E","value":"Cooperation with others","key":"B"}],"title":"You usually prefer"},{"options":[{"result":"J","value":"Likes to have a clear plan","key":"A"},{"result":"P","value":"Prefer to improvise","key":"B"}],"title":"When scheduling events"},{"options":[{"result":"T","value":"Considers that it should be strictly observed","key":"A"},{"result":"F","value":"Considers that flexibility should be applied","key":"B"}],"title":"How do you feel about rules"},{"options":[{"result":"E","value":"Often the talker","key":"A"},{"result":"I","value":"Prefer to listen","key":"B"}],"title":"In social situations"},{"options":[{"result":"J","value":"Research before action","key":"A"},{"result":"P","value":"Learn by doing","key":"B"}],"title":"Facing new challenges"},{"options":[{"result":"S","value":"Attention to detail and facts","key":"A"},{"result":"N","value":"Focus on concepts and imagination","key":"B"}],"title":"in daily life"},{"options":[{"result":"T","value":"More based on logical analyses","key":"A"},{"result":"F","value":"More based on personal feelings","key":"B"}],"title":"When make a decision"},{"options":[{"result":"S","value":"Like to have structure and routine","key":"A"},{"result":"N","value":"Prefers freedom and flexibility","key":"B"}],"title":"For daily arrangements"},{"options":[{"result":"P","value":"Consider the possibilities first","key":"A"},{"result":"J","value":"Consider the consequences first","key":"B"}],"title":"When in trouble"},{"options":[{"result":"T","value":"Time is a precious resource","key":"A"},{"result":"F","value":"Time is a relatively flexible concept","key":"B"}],"title":"How do you feel about time?"}]',
        1, 1, '2024-04-24 16:41:53', '2024-05-09 12:28:58', 0);
INSERT INTO question (id, questionContent, appId, userId, createTime, updateTime, isDelete)
VALUES (2,
        '[{"options":[{"score":0,"value":"Lima","key":"A"},{"score":0,"value":"San Domingo","key":"B"},{"score":0,"value":"San Salvador","key":"C"},{"score":1,"value":"Bogota","key":"D"}],"title":"The capital of Colombia is?"},{"options":[{"score":0,"value":"Montreal","key":"A"},{"score":0,"value":"Toronto","key":"B"},{"score":1,"value":"Ottawa","key":"C"},{"score":0,"value":"Vancouver","key":"D"}],"title":"The capital of Canada is?"},{"options":[{"score":0,"value":"Osaka","key":"A"},{"score":1,"value":"Tokyo","key":"B"},{"score":0,"value":"Kyoto","key":"C"},{"score":0,"value":"Nagoya","key":"D"}],"title":"The capital of Japan is?"},{"options":[{"score":0,"value":"Melbourne","key":"A"},{"score":0,"value":"Sydney","key":"B"},{"score":0,"value":"Brisbane","key":"C"},{"score":1,"value":"Canberra","key":"D"}],"title":"The capital of Australia is?"},{"options":[{"score":1,"value":"Jakarta","key":"A"},{"score":0,"value":"Bangkok","key":"B"},{"score":0,"value":"Ho Chi Minh","key":"C"},{"score":0,"value":"Kuala Lumpur","key":"D"}],"title":"The capital of Indonesia is?"},{"options":[{"score":0,"value":"Shanghai","key":"A"},{"score":0,"value":"Hangzhou","key":"B"},{"score":1,"value":"Beijing","key":"C"},{"score":0,"value":"Guangzhou","key":"D"}],"title":"The capital of China is?"},{"options":[{"score":0,"value":"Hamburg","key":"A"},{"score":0,"value":"Munich","key":"B"},{"score":1,"value":"Berlin","key":"C"},{"score":0,"value":"Cologne","key":"D"}],"title":"The capital of Germany is?"},{"options":[{"score":0,"value":"Busan","key":"A"},{"score":1,"value":"Seoul","key":"B"},{"score":0,"value":"Daejeon","key":"C"},{"score":0,"value":"Incheon","key":"D"}],"title":"The capital of Korea is?"},{"options":[{"score":0,"value":"Guadalajara","key":"A"},{"score":0,"value":"Monterey","key":"B"},{"score":1,"value":"Mexico","key":"C"},{"score":0,"value":"Cancún","key":"D"}],"title":"The capital of Mexico is?"},{"options":[{"score":1,"value":"Cairo","key":"A"},{"score":0,"value":"Alexandria","key":"B"},{"score":0,"value":"Luxor","key":"C"},{"score":0,"value":"Kaliubia","key":"D"}],"title":"The capital of Egypt is?"}]',
        2, 1, '2024-04-25 15:03:07', '2024-05-09 12:28:58', 0);
INSERT INTO question (id, questionContent, appId, userId, createTime, updateTime, isDelete)
VALUES (3,
        '[{"options":[{"result":"I","value":"Work alone","key":"A"},{"result":"E","value":"Cooperation with others","key":"B"}],"title":"You usually prefer"},{"options":[{"result":"J","value":"Likes to have a clear plan","key":"A"},{"result":"P","value":"Prefer to improvise","key":"B"}],"title":"When scheduling events"},{"options":[{"result":"T","value":"Considers that it should be strictly observed","key":"A"},{"result":"F","value":"Considers that flexibility should be applied","key":"B"}],"title":"How do you feel about rules"},{"options":[{"result":"E","value":"Often the talker","key":"A"},{"result":"I","value":"Prefer to listen","key":"B"}],"title":"In social situations"},{"options":[{"result":"J","value":"Research before action","key":"A"},{"result":"P","value":"Learn by doing","key":"B"}],"title":"Facing new challenges"},{"options":[{"result":"S","value":"Attention to detail and facts","key":"A"},{"result":"N","value":"Focus on concepts and imagination","key":"B"}],"title":"in daily life"},{"options":[{"result":"T","value":"More based on logical analyses","key":"A"},{"result":"F","value":"More based on personal feelings","key":"B"}],"title":"When make a decision"},{"options":[{"result":"S","value":"Like to have structure and routine","key":"A"},{"result":"N","value":"Prefers freedom and flexibility","key":"B"}],"title":"For daily arrangements"},{"options":[{"result":"P","value":"Consider the possibilities first","key":"A"},{"result":"J","value":"Consider the consequences first","key":"B"}],"title":"When in trouble"},{"options":[{"result":"T","value":"Time is a precious resource","key":"A"},{"result":"F","value":"Time is a relatively flexible concept","key":"B"}],"title":"How do you feel about time?"}]',
        3, 1, '2024-04-26 16:39:29', '2024-05-09 12:28:58', 0);
INSERT INTO question (id, questionContent, appId, userId, createTime, updateTime, isDelete)
VALUES (4,
        '[{"options":[{"score":0,"value":"Lima","key":"A"},{"score":0,"value":"San Domingo","key":"B"},{"score":0,"value":"San Salvador","key":"C"},{"score":1,"value":"Bogota","key":"D"}],"title":"The capital of Colombia is?"},{"options":[{"score":0,"value":"Montreal","key":"A"},{"score":0,"value":"Toronto","key":"B"},{"score":1,"value":"Ottawa","key":"C"},{"score":0,"value":"Vancouver","key":"D"}],"title":"The capital of Canada is?"},{"options":[{"score":0,"value":"Osaka","key":"A"},{"score":1,"value":"Tokyo","key":"B"},{"score":0,"value":"Kyoto","key":"C"},{"score":0,"value":"Nagoya","key":"D"}],"title":"The capital of Japan is?"},{"options":[{"score":0,"value":"Melbourne","key":"A"},{"score":0,"value":"Sydney","key":"B"},{"score":0,"value":"Brisbane","key":"C"},{"score":1,"value":"Canberra","key":"D"}],"title":"The capital of Australia is?"},{"options":[{"score":1,"value":"Jakarta","key":"A"},{"score":0,"value":"Bangkok","key":"B"},{"score":0,"value":"Ho Chi Minh","key":"C"},{"score":0,"value":"Kuala Lumpur","key":"D"}],"title":"The capital of Indonesia is?"},{"options":[{"score":0,"value":"Shanghai","key":"A"},{"score":0,"value":"Hangzhou","key":"B"},{"score":1,"value":"Beijing","key":"C"},{"score":0,"value":"Guangzhou","key":"D"}],"title":"The capital of China is?"},{"options":[{"score":0,"value":"Hamburg","key":"A"},{"score":0,"value":"Munich","key":"B"},{"score":1,"value":"Berlin","key":"C"},{"score":0,"value":"Cologne","key":"D"}],"title":"The capital of Germany is?"},{"options":[{"score":0,"value":"Busan","key":"A"},{"score":1,"value":"Seoul","key":"B"},{"score":0,"value":"Daejeon","key":"C"},{"score":0,"value":"Incheon","key":"D"}],"title":"The capital of Korea is?"},{"options":[{"score":0,"value":"Guadalajara","key":"A"},{"score":0,"value":"Monterey","key":"B"},{"score":1,"value":"Mexico","key":"C"},{"score":0,"value":"Cancún","key":"D"}],"title":"The capital of Mexico is?"},{"options":[{"score":1,"value":"Cairo","key":"A"},{"score":0,"value":"Alexandria","key":"B"},{"score":0,"value":"Luxor","key":"C"},{"score":0,"value":"Kaliubia","key":"D"}],"title":"The capital of Egypt is?"}]',
        4, 1, '2024-04-26 16:39:29', '2024-05-09 12:28:58', 0);


-- Initial data for scoring results table
INSERT INTO scoring_result (id, resultName, resultDesc, resultPicture, resultProp, resultScoreRange, createTime,
                            updateTime, isDelete, appId, userId)
VALUES (1, 'ISTJ (logistician)', 'Loyal and reliable, recognised as pragmatic and detail-oriented.', 'icon_url_istj', '["I","S","T","J"]', null,
        '2024-04-24 16:57:02', '2024-05-09 12:28:21', 0, 1, 1);
INSERT INTO scoring_result (id, resultName, resultDesc, resultPicture, resultProp, resultScoreRange, createTime,
                            updateTime, isDelete, appId, userId)
VALUES (2, 'ISFJ (Defender)', 'Kind and attentive, characterised by compassion and responsibility.', 'icon_url_isfj', '["I","S","F","J"]', null,
        '2024-04-24 16:57:02', '2024-05-09 12:28:21', 0, 1, 1);
INSERT INTO scoring_result (id, resultName, resultDesc, resultPicture, resultProp, resultScoreRange, createTime,
                            updateTime, isDelete, appId, userId)
VALUES (3, 'INFJ (Advocate)', 'Idealist with deep insight and good understanding of others.', 'icon_url_infj', '["I","N","F","J"]', null,
        '2024-04-24 16:57:02', '2024-05-09 12:28:21', 0, 1, 1);
INSERT INTO scoring_result (id, resultName, resultDesc, resultPicture, resultProp, resultScoreRange, createTime,
                            updateTime, isDelete, appId, userId)
VALUES (4, 'INTJ (Architect)', 'Independent thinker, good at planning and achieving goals, rational and decisive.', 'icon_url_intj', '["I","N","T","J"]', null,
        '2024-04-24 16:57:02', '2024-05-09 12:28:21', 0, 1, 1);
INSERT INTO scoring_result (id, resultName, resultDesc, resultPicture, resultProp, resultScoreRange, createTime,
                            updateTime, isDelete, appId, userId)
VALUES (5, 'ISTP (Virtuoso)', 'Calm and self-possessed, good problem solver, good at practical skills.', 'icon_url_istp', '["I","S","T","P"]', null,
        '2024-04-24 16:57:02', '2024-05-09 12:28:21', 0, 1, 1);
INSERT INTO scoring_result (id, resultName, resultDesc, resultPicture, resultProp, resultScoreRange, createTime,
                            updateTime, isDelete, appId, userId)
VALUES (6, 'ISFP (Adventurer)', 'Artistic and sensitive, cherishing personal space and freedom.', 'icon_url_isfp', '["I","S","F","P"]', null,
        '2024-04-24 16:57:02', '2024-05-09 12:28:21', 0, 1, 1);
INSERT INTO scoring_result (id, resultName, resultDesc, resultPicture, resultProp, resultScoreRange, createTime,
                            updateTime, isDelete, appId, userId)
VALUES (7, 'INFP (Mediator)', 'Idealist, creative, known for compassion and understanding of others.', 'icon_url_infp', '["I","N","F","P"]', null,
        '2024-04-24 16:57:02', '2024-05-09 12:28:21', 0, 1, 1);
INSERT INTO scoring_result (id, resultName, resultDesc, resultPicture, resultProp, resultScoreRange, createTime,
                            updateTime, isDelete, appId, userId)
VALUES (8, 'INTP (Logician)', 'Clear thinker, exploratory spirit, independent thinker and rational.', 'icon_url_intp', '["I","N","T","P"]', null,
        '2024-04-24 16:57:02', '2024-05-09 12:28:21', 0, 1, 1);
INSERT INTO scoring_result (id, resultName, resultDesc, resultPicture, resultProp, resultScoreRange, createTime,
                            updateTime, isDelete, appId, userId)
VALUES (9, 'ESTP (Entrepreneur)', 'Daring and adventurous, quick-thinking and decisive.', 'icon_url_estp', '["E","S","T","P"]', null,
        '2024-04-24 16:57:02', '2024-05-09 12:28:21', 0, 1, 1);
INSERT INTO scoring_result (id, resultName, resultDesc, resultPicture, resultProp, resultScoreRange, createTime,
                            updateTime, isDelete, appId, userId)
VALUES (10, 'ESFP (Entertainer)', 'Enthusiastic, outgoing, sociable, fun-loving and helpful.', 'icon_url_esfp', '["E","S","F","P"]', null,
        '2024-04-24 16:57:02', '2024-05-09 12:28:21', 0, 1, 1);
INSERT INTO scoring_result (id, resultName, resultDesc, resultPicture, resultProp, resultScoreRange, createTime,
                            updateTime, isDelete, appId, userId)
VALUES (11, 'ENFP (Campaigner)', 'Imaginative and enthusiastic, with a knack for inspiring energy and potential in others.', 'icon_url_enfp', '["E","N","F","P"]', null,
        '2024-04-24 16:57:02', '2024-05-09 12:28:21', 0, 1, 1);
INSERT INTO scoring_result (id, resultName, resultDesc, resultPicture, resultProp, resultScoreRange, createTime,
                            updateTime, isDelete, appId, userId)
VALUES (12, 'ENTP (Debater)', 'Creative, debatable, challenges convention and enjoys exploring new territory.', 'icon_url_entp', '["E","N","T","P"]', null,
        '2024-04-24 16:57:02', '2024-05-09 12:28:21', 0, 1, 1);
INSERT INTO scoring_result (id, resultName, resultDesc, resultPicture, resultProp, resultScoreRange, createTime,
                            updateTime, isDelete, appId, userId)
VALUES (13, 'ESTJ (Executive)', 'Pragmatic and decisive, good at organisation and management, with a focus on efficiency and targets.', 'icon_url_estj', '["E","S","T","J"]', null,
        '2024-04-24 16:57:02', '2024-05-09 12:28:21', 0, 1, 1);
INSERT INTO scoring_result (id, resultName, resultDesc, resultPicture, resultProp, resultScoreRange, createTime,
                            updateTime, isDelete, appId, userId)
VALUES (14, 'ESFJ (Consul)', 'Friendly and enthusiastic, characterised by co-ordination, patience and caring, good team player.', 'icon_url_esfj', '["E","S","F","J"]',
        null, '2024-04-24 16:57:02', '2024-05-09 12:28:21', 0, 1, 1);
INSERT INTO scoring_result (id, resultName, resultDesc, resultPicture, resultProp, resultScoreRange, createTime,
                            updateTime, isDelete, appId, userId)
VALUES (15, 'ENFJ (Protagonist)', 'Passionate and caring, good at helping others, leadership and social skills.', 'icon_url_enfj', '["E","N","F","J"]', null,
        '2024-04-24 16:57:02', '2024-05-09 12:28:21', 0, 1, 1);
INSERT INTO scoring_result (id, resultName, resultDesc, resultPicture, resultProp, resultScoreRange, createTime,
                            updateTime, isDelete, appId, userId)
VALUES (16, 'ENTJ (Commander)', 'Decisive and confident, with leadership skills and adept at planning and executing objectives.', 'icon_url_entj', '["E","N","T","J"]', null,
        '2024-04-24 16:57:02', '2024-05-09 12:28:21', 0, 1, 1);
INSERT INTO scoring_result (id, resultName, resultDesc, resultPicture, resultProp, resultScoreRange, createTime,
                            updateTime, isDelete, appId, userId)
VALUES (17, 'Capital Knowledge Guru', 'You are awesome and your knowledge of capital cities is outstanding!', null, null, 9, '2024-04-25 15:05:44', '2024-05-09 12:28:21',
        0, 2, 1);
INSERT INTO scoring_result (id, resultName, resultDesc, resultPicture, resultProp, resultScoreRange, createTime,
                            updateTime, isDelete, appId, userId)
VALUES (18, 'Geography master！', 'You know the world''s capitals pretty well, but there are still a few little things you need to work on!', null, null, 7,
        '2024-04-25 15:05:44', '2024-05-09 12:28:21', 0, 2, 1);
INSERT INTO scoring_result (id, resultName, resultDesc, resultPicture, resultProp, resultScoreRange, createTime,
                            updateTime, isDelete, appId, userId)
VALUES (19, 'Keep going!', 'Still need to work on it.', null, null, 0, '2024-04-25 15:05:44', '2024-05-09 12:28:21', 0, 2, 1);

-- Initial data for the user response form
INSERT INTO user_answer (id, appId, appType, choices, resultId, resultName, resultDesc, resultPicture, resultScore,
                              scoringStrategy, userId, createTime, updateTime, isDelete)
VALUES (1, 1, 1, '["A","A","A","B","A","A","A","B","B","A"]', 1, 'ISTJ (logistician)', 'Loyal and reliable, recognised as pragmatic and detail-oriented.', 'icon_url_istj',
        null, 0, 1, '2024-05-09 15:08:22', '2024-05-09 15:10:13', 0);
INSERT INTO user_answer (id, appId, appType, choices, resultId, resultName, resultDesc, resultPicture, resultScore,
                              scoringStrategy, userId, createTime, updateTime, isDelete)
VALUES (2, 2, 0, '["D","C","B","D","A","C","C","B","C","A"]', 17, 'Capital Knowledge Guru', 'You are awesome and your knowledge of capital cities is outstanding!', null, 10, 0, 1,
        '2024-05-09 15:08:36', '2024-05-09 15:10:13', 0);
