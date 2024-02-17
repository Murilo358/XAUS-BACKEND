create table if not exists
  public.password_tokens (
    token_id bigserial,
    expiration_time timestamp without time zone null,
    token character varying(255) null,
    user_id bigint null,
    constraint password_tokens_pkey primary key (token_id),
    constraint fkcfy8uf6yvjt7x8wcfkyisr8jf foreign key (user_id) references users (id)
  ) tablespace pg_default;