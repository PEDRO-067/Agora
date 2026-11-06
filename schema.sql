CREATE TABLE artigo (
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