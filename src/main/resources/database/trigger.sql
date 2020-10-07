CREATE FUNCTION emp_stamp() RETURNS trigger AS $$
BEGIN
    NEW.usd_amount = round((products.price/2.7)::numeric, 3);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER emp_stamp BEFORE INSERT ON orders
    FOR EACH ROW EXECUTE PROCEDURE emp_stamp();

-- delete from products;
-- delete from orders;
--
-- INSERT INTO orders (byn_amount, date, usd_amount, customer_id) VALUES (3.5, '1/9/2020', 0, 2),
--                                                                       (1.0,'1/9/2020', 0, 3),
--                                                                       (0.59,'25/8/2020', 0, 4),
--                                                                       (3.19,'10/9/2020', 0, 2),
--                                                                       (2.15,'7/9/2020', 0, 5),
--                                                                       (5.0,'30/8/2020',  0, 5);
-- INSERT INTO products (price, title, order_id, seller_id)VALUES (2.0, 'Salt', 1, 1),
--                                                                (1.5, 'Sugar', 1, 2),
--                                                                (1.0, 'Bread', 2, 2),
--                                                                (0.59, 'Carrot', 3, 5),
--                                                                (3.19, 'Apples', 4, 5),
--                                                                (2.15, 'Milk', 5, 6),
--                                                                (2.0, 'Water', 6, 3),
--                                                                (3.0, 'fish', 6, 4);

select * from products;

-- CREATE TABLE public.products
-- (
--     id bigint DEFAULT NEXTVAL('products_id_seq'),
--     title character varying(30),
--     order_id bigint,
--     seller_id bigint,
--     CONSTRAINT order_key FOREIGN KEY (order_id)
--         REFERENCES public.orders (id) MATCH SIMPLE
--         ON UPDATE NO ACTION
--         ON DELETE SET NULL,
--     CONSTRAINT seller_key FOREIGN KEY (seller_id)
--         REFERENCES public.sellers (id) MATCH SIMPLE
--         ON UPDATE NO ACTION
--         ON DELETE CASCADE
-- )
--     WITH (
--         OIDS = FALSE
--     );
--
-- ALTER TABLE public.products
--     OWNER to postgres;