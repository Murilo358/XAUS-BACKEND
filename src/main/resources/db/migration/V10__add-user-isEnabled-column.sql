ALTER TABLE public.users ADD COLUMN IF NOT EXISTS is_enabled boolean not null default(false);