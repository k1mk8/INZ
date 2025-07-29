-- 1. Tabela administratorów
DROP TABLE IF EXISTS news CASCADE;
DROP TABLE IF EXISTS administrators CASCADE;

CREATE TABLE administrators (
  id              SERIAL PRIMARY KEY,
  username        VARCHAR(50) NOT NULL UNIQUE,
  email           VARCHAR(255) NOT NULL UNIQUE,
  password_hash   VARCHAR(255) NOT NULL
);

-- 2. Tabela wiadomości
CREATE TABLE news (
  id              SERIAL PRIMARY KEY,
  title           VARCHAR(200) NOT NULL,
  content         TEXT NOT NULL,
  author_id       INTEGER NOT NULL REFERENCES administrators(id) ON DELETE RESTRICT,
  is_published    BOOLEAN NOT NULL DEFAULT FALSE,
  published_at    TIMESTAMPTZ
);

-- 3. Dodaj przykładowego admina
-- Hasło: admin123 (bcrypt hash)
INSERT INTO administrators (username, email, password_hash)
VALUES ('administrator', 'admin@example.com', '013c6889f799cd986a735118e1888727d1435f7f623d05d58c61bf2cd8b49ac90105e5786ceaabd62bbc27336153d0d316b2d13b36804080c44aa6198c533215');

-- 4. Dodaj aktualności
INSERT INTO news (title, content, author_id, is_published, published_at)
VALUES 
(
  'Planowany nabór wniosków w ramach działania 2.3 Cyfrowe Lubelskie w ochronie zdrowia programu Fundusze Europejskie dla Lubelskiego',
  'Zapraszamy instytucje ochrony zdrowia do współpracy w zakresie opracowania dokumentacji aplikacyjnej dla projektów ubiegających się o dofinansowanie w ramach działania 2.3 Cyfrowe Lubelskie w ochronie zdrowia programu Fundusze Europejskie dla Lubelskiego
Nabór wniosków planowany jest od 20.10 do 20.11.2025 roku.
O dofinansowanie mogą ubiegać się projekty:
- z zakresu usług e-zdrowia oraz informatyzacji jednostek w sektorze ochrony zdrowia mające na celu zapewnienie interoperacyjności i integrację systemów informatycznych świadczeniodawców z centralną architekturą informatyczną e-zdrowia, w tym: wsparcie rozwoju elektronicznej dokumentacji medycznej, rozwiązań z zakresu telemedycyny, sztucznej inteligencji oraz cyfryzacji procesów back-office i rozwoju infrastruktury informatycznej służącej poprawie dojrzałości cyfrowej placówek medycznych;
- z zakresu rozwoju cyberbezpieczeństwa, tj. wzmacniające bezpieczeństwo świadczenia e-usług lub systemów informatycznych poprzez budowę lub modernizację istniejących systemów, o zasięgu regionalnym i lokalnym
Łączna pula środków przeznaczonych dla działania: 35 713 635,92 zł',
  1, TRUE, NOW()
),
(
  'Weryfikacji infrastruktury pod względem wpływu na klimat w latach 2021–2027',
  'Zapraszamy Wnioskodawców /Beneficjentów funduszy europejskich  do współpracy w zakresie opracowania analizy odporność infrastruktury na zmiany klimatu. 
Projekty infrastrukturalne w perspektywie 2021-2027 podlegają obowiązkowi weryfikacji pod względem wpływu na klimat zgodnie z metodologia wynikającą z Zawiadomienia Komisji Europejskiej z 16.09.2021 r. pn. „Wytyczne techniczne dotyczące weryfikacji infrastruktury pod względem wpływu na klimat w latach 2021–2027 (2021/C 373/01). 
W ramach naborów do działań programu Fundusze Europejskie dla Lubelskiego 2021-2027 Wnioskodawcy zobowiązani są do przedłożenia oświadczenia o przeprowadzonej weryfikacji projektu oraz opisania wniosków wynikających z analizy wpływu na klimat w sekcji I wniosku o dofinansowanie. Instytucja organizująca nabór ma prawo poprosić o przedstawienie dokumentacji dotyczącej weryfikacji projektu pod względem wpływu na klimat i uzyskania dodatkowych informacji na temat dokumentów. 
Powyższy wymóg zobligował Wnioskodawców do wykonania analizy odporność infrastruktury na zmiany klimatu dla projektów współfinansowanych ze środków zewnętrznych. Analiza powinna być wykonana przed przystąpieniem do realizacji projektu - na etapie prac przygotowawczych. Weryfikacja pod względem wpływu na klimat projektu dokonywana jest  w ramach dwóch filarów tj.: 
1) Neutralność klimatyczna – łagodzenie zmian klimatu oraz 
2) Odporność na zmianę klimatu - przystosowanie się do zmiany klimatu. 
Każdy z filarów obejmuje etap preselekcji a następnie (w uzależnieniu od wyników preselekcji) etap szczegółowej analizy.
Koszt przeprowadzenia analizy uzależniony jest od zakresu inwestycji, powiązanej z potrzebą obliczenia śladu węglowego. 
Zapraszamy do kontaktu i zapoznania się  ze szczegółową ofertą.',
  1, TRUE, NOW()
);