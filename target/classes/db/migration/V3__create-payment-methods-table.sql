create table
  public.payment_methods (
    id bigserial,
    payment_method character varying(255) null,
    constraint payment_methods_pkey primary key (id)
  ) tablespace pg_default;