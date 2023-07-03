INSERT INTO public.projects
(project_name, created_at, updated_at, created_by, modified_by)
VALUES('Webshop', NOW(), NOW(), 'SYSTEM', 'SYSTEM') ON CONFLICT DO NOTHING;
INSERT INTO public.projects
(project_name, created_at, updated_at, created_by, modified_by)
VALUES('Issue Tracking', NOW(), NOW(), 'SYSTEM', 'SYSTEM') ON CONFLICT DO NOTHING;
INSERT INTO public.projects
(project_name, created_at, updated_at, created_by, modified_by)
VALUES('Ecommerce', NOW(), NOW(), 'SYSTEM', 'SYSTEM') ON CONFLICT DO NOTHING;

INSERT INTO public.roles
(role_name)
VALUES('ROLE_ADMIN') ON CONFLICT DO NOTHING;
INSERT INTO public.roles
(role_name)
VALUES('ROLE_USER') ON CONFLICT DO NOTHING;
INSERT INTO public.roles
(role_name)
VALUES('ROLE_VIEWER') ON CONFLICT DO NOTHING;

INSERT INTO public.ticket_priority
(priority)
VALUES('low') ON CONFLICT DO NOTHING;
INSERT INTO public.ticket_priority
(priority)
VALUES('normal') ON CONFLICT DO NOTHING;
INSERT INTO public.ticket_priority
(priority)
VALUES('high') ON CONFLICT DO NOTHING;
INSERT INTO public.ticket_priority
(priority)
VALUES('immediate') ON CONFLICT DO NOTHING;

INSERT INTO public.ticket_status
(status)
VALUES('new') ON CONFLICT DO NOTHING;
INSERT INTO public.ticket_status
(status)
VALUES('in progress') ON CONFLICT DO NOTHING;
INSERT INTO public.ticket_status
(status)
VALUES('to be tested') ON CONFLICT DO NOTHING;
INSERT INTO public.ticket_status
(status)
VALUES('closed') ON CONFLICT DO NOTHING;
INSERT INTO public.ticket_status
(status)
VALUES('on hold') ON CONFLICT DO NOTHING;

INSERT INTO public.ticket_type
(type_name)
VALUES('bug') ON CONFLICT DO NOTHING;
INSERT INTO public.ticket_type
(type_name)
VALUES('feature') ON CONFLICT DO NOTHING;
INSERT INTO public.ticket_type
(type_name)
VALUES('test') ON CONFLICT DO NOTHING;

INSERT INTO public.users
(username, password_, first_name, last_name, email, created_at, updated_at, created_by, modified_by)
VALUES('admin', '$2y$10$Yu.mXFCnl7yc58v5LGu2Xu2dbuL0yWXt1plqTlLKUpEJ8napojaR6', 'First', 'Admin', 'firstadmin@issuetracker.com', NOW(), NOW(), 'SYSTEM', 'SYSTEM') ON CONFLICT DO NOTHING;
INSERT INTO public.users
(username, password_, first_name, last_name, email, created_at, updated_at, created_by, modified_by)
VALUES('user', '$2y$10$Yu.mXFCnl7yc58v5LGu2Xu2dbuL0yWXt1plqTlLKUpEJ8napojaR6', 'First', 'User', 'firstuser@issuetracker.com', NOW(), NOW(), 'SYSTEM', 'SYSTEM') ON CONFLICT DO NOTHING;
INSERT INTO public.users
(username, password_, first_name, last_name, email, created_at, updated_at, created_by, modified_by)
VALUES('viewer', '$2y$10$Yu.mXFCnl7yc58v5LGu2Xu2dbuL0yWXt1plqTlLKUpEJ8napojaR6', 'First', 'Viewer', 'firstviewer@issuetracker.com', NOW(), NOW(), 'SYSTEM', 'SYSTEM') ON CONFLICT DO NOTHING;

INSERT INTO public.user_roles
(user_id, role_id)
VALUES(1, 1) ON CONFLICT DO NOTHING;
INSERT INTO public.user_roles
(user_id, role_id)
VALUES(2, 2) ON CONFLICT DO NOTHING;
INSERT INTO public.user_roles
(user_id, role_id)
VALUES(3, 3) ON CONFLICT DO NOTHING;