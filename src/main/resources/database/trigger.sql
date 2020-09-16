CREATE FUNCTION emp_stamp() RETURNS trigger AS $$
BEGIN
    NEW.price = round((NEW.price/2.7)::numeric, 3);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER emp_stamp BEFORE INSERT ON products
    FOR EACH ROW EXECUTE PROCEDURE emp_stamp();