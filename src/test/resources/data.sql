-- =====================
-- USERS
-- =====================

-- Admin pwd: admin
-- Carlos pwd: carlos
-- Lucia pwd: lucia
-- Laura pwd: laura

INSERT INTO users (id, display_name, password, email, role, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'Admin', '$2a$10$15S1yZ8FQu/r2n0So0MrAeLmIs3IVb2tQ9DlMrmUzvlOfGE0EHz2O',
        'admin@fbank.com', 'ADMIN', NOW()),
       ('1c9e3f6a-3b1a-4d5f-b7a5-123456789abc', 'Carlos García',
        '$2a$10$gCLwojiBmjzVhnb5xkBqqugOS0jeVFTN.lkjzEZZHVBHyjYIaDln6', 'carlos.garcia@mail.com', 'CUSTOMER', NOW()),
       ('2d8b7e5c-5f1d-4c8a-a9b6-abcdef123456', 'Lucía Fernández',
        '$2a$10$Qy24/x8yz8iCd8BUalXBIe/5ZmKuZejkBOV.862iqaSMo4dAZP4QG', 'lucia.fernandez@mail.com', 'CUSTOMER', NOW()),
       ('aaaaaaaa-3333-4444-8888-cccccccc3333', 'LauraJM',
        '$2a$12$RQzK7upOs7jLpN6AylWbq.Pv61jF2tyJkn/0Jnwc0TY0AObaYqxTe', 'laura.jimenez@mail.com', 'CUSTOMER', NOW());

-- =====================
-- ADDRESSES
-- =====================
INSERT INTO addresses (id, street_name, street_number, floor, door, postal_code, city, province, deleted, created_at)
VALUES ('7b9f3c2a-1111-4e2b-8d3a-1a2b3c4d5e6f', 'Calle Mayor', '12', '3', 'B', '28013', 'Madrid', 'Madrid', false,
        NOW()),
       ('8c2e1d4f-2222-4f5b-9c6d-2b3c4d5e6f7a', 'Avenida de España', '45', '1', 'A', '08002', 'Barcelona', 'Barcelona',
        false, NOW());

-- =====================
-- CUSTOMERS
-- =====================
INSERT INTO customers (id, name, last_name, document_id, address, age, phone, user_id, deleted, created_at)
VALUES ('a1b2c3d4-1111-4e2b-8f3a-111122223333', 'Carlos', 'García', '12345678Z', '7b9f3c2a-1111-4e2b-8d3a-1a2b3c4d5e6f',
        34, '612345678', '1c9e3f6a-3b1a-4d5f-b7a5-123456789abc', false, NOW()),
       ('b2c3d4e5-2222-4f5b-9c6d-222233334444', 'Lucía', 'Fernández', '87654321X',
        '8c2e1d4f-2222-4f5b-9c6d-2b3c4d5e6f7a', 29, '698765432', '2d8b7e5c-5f1d-4c8a-a9b6-abcdef123456', false, NOW());

-- =====================
-- ACCOUNTS
-- =====================
INSERT INTO accounts (id, balance, iban, status, created_at)
VALUES ('c1d2e3f4-1111-4a2b-8d3a-111122223333', 200.00, 'ES8621006568558637351385', 'ACTIVE', NOW()),
       ('c2d3e4f5-1111-4b2c-8d3b-111122223334', 500.00, 'ES5220807767053411166888', 'ACTIVE', NOW()),
       ('d1e2f3a4-2222-4c2d-9f3c-222233334444', 315.00, 'ES2400817533191348763796', 'ACTIVE', NOW()),
       ('d2e3f4b5-2222-4d2e-9f3d-222233334445', 555.00, 'ES5220381647133735731647', 'ACTIVE', NOW()),
       ('c397c4e8-f905-4d82-8f31-3517d721cde8', 0.00, 'ES6200755871757639877295', 'ACTIVE', NOW()),
       ('8ed4b1c5-5710-4124-b2e6-85b5159025b5', 0.00, 'ES4604878653828295376821', 'INACTIVE', NOW());

-- =====================
-- CUSTOMER_ACCOUNT RELATIONS
-- =====================
INSERT INTO customer_accounts (id, customer_id, account_id, is_owner, deleted, created_at)
VALUES ('e1f2a3b4-1111-4a2b-8d3a-111122223335', 'a1b2c3d4-1111-4e2b-8f3a-111122223333',
        'c1d2e3f4-1111-4a2b-8d3a-111122223333', true, false, NOW()),
       ('e2f3b4c5-1111-4b2c-8d3b-111122223336', 'a1b2c3d4-1111-4e2b-8f3a-111122223333',
        'c2d3e4f5-1111-4b2c-8d3b-111122223334', true, false, NOW()),
       ('f1a2b3c4-2222-4c2d-9f3c-222233334446', 'b2c3d4e5-2222-4f5b-9c6d-222233334444',
        'd1e2f3a4-2222-4c2d-9f3c-222233334444', true, false, NOW()),
       ('f2b3c4d5-2222-4d2e-9f3d-222233334447', 'b2c3d4e5-2222-4f5b-9c6d-222233334444',
        'd2e3f4b5-2222-4d2e-9f3d-222233334445', true, false, NOW()),
       ('96f7f344-b196-4061-aaed-6b9751ca6971', 'b2c3d4e5-2222-4f5b-9c6d-222233334444',
        'c2d3e4f5-1111-4b2c-8d3b-111122223334', true, false, NOW()),
       ('649e5b14-0add-482b-951a-e8be5652548f', 'a1b2c3d4-1111-4e2b-8f3a-111122223333',
        'c397c4e8-f905-4d82-8f31-3517d721cde8', true, false, NOW()),
       ('46068748-923b-4dec-b301-aa8133fb29d4', 'a1b2c3d4-1111-4e2b-8f3a-111122223333',
        '8ed4b1c5-5710-4124-b2e6-85b5159025b5', true, false, NOW());


-- =====================
-- TRANSACTIONS
-- =====================
-- Account 1 Carlos
INSERT INTO transactions (id, origin_account, amount, beneficiary_name, concept, beneficiary_iban, status, type,
                          created_at)
VALUES ('20354d7a-e4fe-47af-8ff6-187bca92f3f9', 'c1d2e3f4-1111-4a2b-8d3a-111122223333', 50.00, 'Ana Gómez',
        'Rent payment', 'ES1300817249517933221598', 'COMPLETED', 'TRANSFER', NOW()),
       ('cfac032a-8171-4078-b97f-465a9a884ba0', 'c1d2e3f4-1111-4a2b-8d3a-111122223333', 30.00, 'Mercadona',
        'Supermarket', 'ES4800816756535728535236', 'COMPLETED', 'TRANSFER', NOW()),
       ('62bee49e-89c6-4a05-9bbe-b14ec0b598c8', 'c1d2e3f4-1111-4a2b-8d3a-111122223333', 40.00, 'Iberdrola',
        'Electricity', 'ES7930045865804725582161', 'COMPLETED', 'TRANSFER', NOW()),
       ('b49640e0-f4ef-4db9-a242-55ba3b1b81fb', 'c1d2e3f4-1111-4a2b-8d3a-111122223333', 35.00, 'Netflix',
        'Subscription', 'ES9114657699651463218184', 'COMPLETED', 'TRANSFER', NOW()),
       ('aac2d36e-b84a-4f8e-8c56-f696d1839948', 'c1d2e3f4-1111-4a2b-8d3a-111122223333', 45.00, 'Gym', 'Monthly fee',
        'ES0604876114181976468235', 'COMPLETED', 'TRANSFER', NOW());

-- Account 2 Carlos
INSERT INTO transactions (id, origin_account, amount, beneficiary_name, concept, beneficiary_iban, status, type,
                          created_at)
VALUES ('aa96b9df-bd3e-45df-9f90-3b8f2b86ce29', 'c2d3e4f5-1111-4b2c-8d3b-111122223334', 150.00, 'Amazon', 'Shopping',
        'ES6200758184816946894619', 'COMPLETED', 'TRANSFER', NOW()),
       ('f412a3bf-8e53-4929-b7e3-600730083914', 'c2d3e4f5-1111-4b2c-8d3b-111122223334', 120.00, 'Vodafone', 'Internet',
        'ES1701825859473727853937', 'COMPLETED', 'TRANSFER', NOW()),
       ('de1e27bc-404a-4219-b449-1aad13d1ce12', 'c2d3e4f5-1111-4b2c-8d3b-111122223334', 80.00, 'El Corte Inglés',
        'Clothing', 'ES6704874916661894373833', 'COMPLETED', 'TRANSFER', NOW()),
       ('0038e4cd-8454-40a8-83a2-7ae3fab90fd8', 'c2d3e4f5-1111-4b2c-8d3b-111122223334', 70.00, 'Renfe', 'Ticket',
        'ES9704876836018974978711', 'COMPLETED', 'TRANSFER', NOW()),
       ('a75a2ff5-3cd2-4e42-8eca-ef809df7bfe2', 'c2d3e4f5-1111-4b2c-8d3b-111122223334', 80.00, 'Pharmacy', 'Medicines',
        'ES8621006568558637351385', 'COMPLETED', 'TRANSFER', NOW());

-- Account 1 Lucía
INSERT INTO transactions (id, origin_account, amount, beneficiary_name, concept, beneficiary_iban, status, type,
                          created_at)
VALUES ('73d49922-100e-4233-ad16-f30566e93c5c', 'd1e2f3a4-2222-4c2d-9f3c-222233334444', 100.00, 'Supercor',
        'Supermarket', 'ES5220807767053411166888', 'COMPLETED', 'TRANSFER', NOW()),
       ('46a4dbd8-d51a-4285-95b8-3c4fb5ce798e', 'd1e2f3a4-2222-4c2d-9f3c-222233334444', 60.00, 'Endesa', 'Electricity',
        'ES2400817533191348763796', 'COMPLETED', 'TRANSFER', NOW()),
       ('2c105c60-4013-4755-ad57-21befc32d226', 'd1e2f3a4-2222-4c2d-9f3c-222233334444', 50.00, 'Netflix',
        'Subscription', 'ES5220381647133735731647', 'COMPLETED', 'TRANSFER', NOW()),
       ('b7d8fcae-17d3-444c-ae0c-bb33acbfb768', 'd1e2f3a4-2222-4c2d-9f3c-222233334444', 55.00, 'Mercadona',
        'Supermarket', 'ES1300817249517933221598', 'COMPLETED', 'TRANSFER', NOW()),
       ('c731e304-53d5-4bd9-8fbe-5d4169bab7d6', 'd1e2f3a4-2222-4c2d-9f3c-222233334444', 50.00, 'Gym', 'Monthly fee',
        'ES4800816756535728535236', 'COMPLETED', 'TRANSFER', NOW());

-- Account 2 Lucía
INSERT INTO transactions (id, origin_account, amount, beneficiary_name, concept, beneficiary_iban, status, type,
                          created_at)
VALUES ('831a2080-07ce-4acf-9fc5-b588c30d6c2c', 'd2e3f4b5-2222-4d2e-9f3d-222233334445', 120.00, 'Amazon', 'Shopping',
        'ES7930045865804725582161', 'COMPLETED', 'TRANSFER', NOW()),
       ('fb6281fe-ded1-47a5-869d-bbc91e4e709e', 'd2e3f4b5-2222-4d2e-9f3d-222233334445', 105.00, 'Vodafone', 'Internet',
        'ES9114657699651463218184', 'COMPLETED', 'TRANSFER', NOW()),
       ('bb0d5100-63a4-45fd-8dcf-b433ef1fbe2f', 'd2e3f4b5-2222-4d2e-9f3d-222233334445', 110.00, 'El Corte Inglés',
        'Clothing', 'ES0604876114181976468235', 'COMPLETED', 'TRANSFER', NOW()),
       ('0bf27bf5-ad65-4d6c-b994-54e3296e028c', 'd2e3f4b5-2222-4d2e-9f3d-222233334445', 60.00, 'Renfe', 'Ticket',
        'ES6200758184816946894619', 'COMPLETED', 'TRANSFER', NOW()),
       ('de536bf8-c36f-4fc3-8b44-4770b3a7eb2f', 'd2e3f4b5-2222-4d2e-9f3d-222233334445', 95.00, 'Pharmacy', 'Medicines',
        'ES1701825859473727853937', 'COMPLETED', 'TRANSFER', NOW());
