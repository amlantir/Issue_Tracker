-- public.projects definition

-- Drop table

-- DROP TABLE public.projects;

CREATE TABLE public.projects
(
    id           int8        NOT NULL GENERATED ALWAYS AS IDENTITY,
    project_name text        NOT NULL UNIQUE,
    created_at   timestamptz NOT NULL,
    updated_at   timestamptz NOT NULL,
    created_by   text        NOT NULL,
    modified_by  text        NOT NULL,
    CONSTRAINT projects_pkey PRIMARY KEY (id)
);


-- public.ticket_priority definition

-- Drop table

-- DROP TABLE public.ticket_priority;

CREATE TABLE public.ticket_priority
(
    id       int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
    priority text NOT NULL UNIQUE,
    CONSTRAINT ticket_priority_pkey PRIMARY KEY (id)
);


-- public.ticket_status definition

-- Drop table

-- DROP TABLE public.ticket_status;

CREATE TABLE public.ticket_status
(
    id     int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
    status text NOT NULL UNIQUE,
    CONSTRAINT ticket_status_pkey PRIMARY KEY (id)
);


-- public.ticket_type definition

-- Drop table

-- DROP TABLE public.ticket_type;

CREATE TABLE public.ticket_type
(
    id        int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
    type_name text NOT NULL UNIQUE,
    CONSTRAINT ticket_type_pkey PRIMARY KEY (id)
);


-- public.users definition

-- Drop table

-- DROP TABLE public.users;

CREATE TABLE public.users
(
    id          int8        NOT NULL GENERATED ALWAYS AS IDENTITY,
    username    text        NOT NULL UNIQUE,
    password_   text        NOT NULL,
    first_name  text        NOT NULL,
    last_name   text        NOT NULL,
    email       text        NOT NULL,
    created_at  timestamptz NOT NULL,
    updated_at  timestamptz NOT NULL,
    created_by  text        NOT NULL,
    modified_by text        NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id)
);


-- public.tickets definition

-- Drop table

-- DROP TABLE public.tickets;

CREATE TABLE public.tickets
(
    id          int8        NOT NULL GENERATED ALWAYS AS IDENTITY,
    issue_name  text        NOT NULL,
    created_by  text        NOT NULL,
    created_at  timestamptz NOT NULL,
    updated_at  timestamptz NOT NULL,
    modified_by text        NOT NULL,
    description text        NOT NULL,
    project_id  int8        NOT NULL,
    status_id   int8        NOT NULL,
    type_id     int8        NOT NULL,
    priority_id int8        NOT NULL,
    CONSTRAINT ticket_pkey PRIMARY KEY (id),
    CONSTRAINT fk_priority_id FOREIGN KEY (priority_id) REFERENCES public.ticket_priority (id),
    CONSTRAINT fk_project_id FOREIGN KEY (project_id) REFERENCES public.projects (id),
    CONSTRAINT fk_status_id FOREIGN KEY (status_id) REFERENCES public.ticket_status (id),
    CONSTRAINT fk_type_id FOREIGN KEY (type_id) REFERENCES public.ticket_type (id)
);


-- public.file_datas definition

-- Drop table

-- DROP TABLE public.file_datas;

CREATE TABLE public.file_datas
(
    id          int8        NOT NULL GENERATED ALWAYS AS IDENTITY,
    ticket_id   int8        NOT NULL,
    file_path   text        NOT NULL,
    file_name   text        NOT NULL,
    file_type   text        NOT NULL,
    file_size   int8        NOT NULL,
    created_at  timestamptz NOT NULL,
    updated_at  timestamptz NOT NULL,
    created_by  text        NOT NULL,
    modified_by text        NOT NULL,
    CONSTRAINT files_pkey PRIMARY KEY (id),
    CONSTRAINT fk_ticket_id FOREIGN KEY (ticket_id) REFERENCES public.tickets (id)
);


-- public.notifications definition

-- Drop table

-- DROP TABLE public.notifications;

CREATE TABLE public.notifications
(
    id          int8        NOT NULL GENERATED ALWAYS AS IDENTITY,
    ticket_id   int8        NOT NULL,
    user_id     int8        NOT NULL,
    is_seen     bool        NOT NULL,
    created_at  timestamptz NOT NULL,
    updated_at  timestamptz NOT NULL,
    created_by  text        NOT NULL,
    modified_by text        NOT NULL,
    CONSTRAINT notifications_pkey PRIMARY KEY (id),
    CONSTRAINT fk_ticket_id FOREIGN KEY (ticket_id) REFERENCES public.tickets (id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES public.users (id)
);


-- public.ticket_comments definition

-- Drop table

-- DROP TABLE public.ticket_comments;

CREATE TABLE public.ticket_comments
(
    id             int8        NOT NULL GENERATED ALWAYS AS IDENTITY,
    ticket_id      int8        NOT NULL,
    ticket_comment text        NOT NULL,
    created_at     timestamptz NOT NULL,
    updated_at     timestamptz NOT NULL,
    created_by     text        NOT NULL,
    modified_by    text        NOT NULL,
    CONSTRAINT ticket_comments_pkey PRIMARY KEY (id),
    CONSTRAINT fk_ticket_id FOREIGN KEY (ticket_id) REFERENCES public.tickets (id)
);


-- public.ticket_users definition

-- Drop table

-- DROP TABLE public.ticket_users;

CREATE TABLE public.ticket_users
(
    user_id   int8 NOT NULL,
    ticket_id int8 NOT NULL,
    CONSTRAINT fk_ticket_id FOREIGN KEY (ticket_id) REFERENCES public.tickets (id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES public.users (id)
);


-- public.roles definition

-- Drop table

-- DROP TABLE public.roles;

CREATE TABLE public.roles
(
    id        int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
    role_name text NOT NULL UNIQUE,
    CONSTRAINT roles_pkey PRIMARY KEY (id)
);


-- public.user_roles definition

-- Drop table

-- DROP TABLE public.user_roles;

CREATE TABLE public.user_roles
(
    user_id int8 NOT NULL UNIQUE,
    role_id int8 NOT NULL
);


-- public.roles foreign keys

-- public.user_roles foreign keys

ALTER TABLE public.user_roles
    ADD CONSTRAINT fk_role_id FOREIGN KEY (role_id) REFERENCES public.roles (id);
ALTER TABLE public.user_roles
    ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES public.users (id);