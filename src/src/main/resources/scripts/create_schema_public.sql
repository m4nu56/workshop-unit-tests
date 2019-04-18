create table if not exists t_contrat
(
    id         bigserial        not null
        constraint t_contrat_pk
            primary key,
    code       varchar          not null,
    montant    double precision not null,
    code_taux  varchar,
    duree      integer          not null,
    date_debut date             not null
);

create unique index if not exists t_contrat_code_uindex
    on t_contrat (code);

create table if not exists t_echeance
(
    id         bigserial        not null
        constraint echeance_pk
            primary key,
    id_contrat bigint           not null
        constraint echeance_t_contrat_id_fk
            references t_contrat,
    capital    double precision not null,
    interets   double precision not null,
    date       date             not null,
    encours    double precision not null
);

