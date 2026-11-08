CREATE TABLE IF NOT EXISTS artigo (
    id              INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    doi             TEXT NOT NULL UNIQUE,
    titulo          TEXT,
    autores         TEXT[] NOT NULL,
    ano             INTEGER,
    tipo_publicacao TEXT,
    veiculo         TEXT,
    volume          TEXT,
    numero          TEXT,
    numero_artigo   TEXT,
    editora         TEXT,
    organizacao     TEXT,
    endereco        TEXT,
    paginas         TEXT,
    mes             INTEGER,
    resumo          TEXT,
    url             TEXT,
    status_leitura  TEXT NOT NULL
        CHECK (status_leitura IN ('Não lido', 'Em leitura', 'Lido'))
);

-- Uma anotação por artigo, conforme o Model atual.
CREATE TABLE IF NOT EXISTS anotacao (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_artigo INTEGER NOT NULL UNIQUE REFERENCES artigo(id) ON DELETE CASCADE,
    nome TEXT NOT NULL CHECK (btrim(nome) <> ''),
    texto TEXT NOT NULL CHECK (btrim(texto) <> ''),
    dataCriacao DATE NOT NULL,
    dataEdicao DATE
);

CREATE TABLE IF NOT EXISTS pasta (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome TEXT NOT NULL CHECK (btrim(nome) <> '')
);

CREATE TABLE IF NOT EXISTS tag (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome TEXT NOT NULL CHECK (btrim(nome) <> '')
);
CREATE UNIQUE INDEX IF NOT EXISTS tag_nome_unico ON tag (lower(btrim(nome)));

CREATE TABLE IF NOT EXISTS pasta_artigo (
    id_pasta INTEGER NOT NULL REFERENCES pasta(id) ON DELETE CASCADE,
    id_artigo INTEGER NOT NULL REFERENCES artigo(id) ON DELETE CASCADE,
    PRIMARY KEY (id_pasta, id_artigo)
);

CREATE TABLE IF NOT EXISTS artigo_tag (
    id_artigo INTEGER NOT NULL REFERENCES artigo(id) ON DELETE CASCADE,
    id_tag INTEGER NOT NULL REFERENCES tag(id) ON DELETE CASCADE,
    PRIMARY KEY (id_artigo, id_tag)
);
