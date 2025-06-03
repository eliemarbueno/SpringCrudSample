CREATE TABLE public.categories (
	category_id uuid NOT NULL,
	category_description varchar(765) NULL,
	category_image_url varchar(765) NULL,
	category_name varchar(255) NOT NULL,
    CONSTRAINT categories_pkey PRIMARY KEY (category_id)
);

create index categories_category_name_idx on public.categories (category_name);
