DROP FUNCTION IF EXISTS goldProduct;
DELIMITER $$
CREATE FUNCTION goldProduct()
RETURNS TEXT
BEGIN
	DECLARE result TEXT;
	SET result = 
		select distinct (
			select p.name from product p where p.id = o.product_id) as product, 
			max((select sum(amount) from orders where product_id = o.product_id)) as how_many 
		from orders o;
	RETURN result;
END$$
DELIMITER ;