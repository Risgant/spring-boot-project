CREATE FUNCTION emp_stamp() RETURNS trigger AS $$
BEGIN
    NEW.usd_amount = round((products.price/2.7)::numeric, 3);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER emp_stamp BEFORE INSERT ON orders
    FOR EACH ROW EXECUTE PROCEDURE emp_stamp();
