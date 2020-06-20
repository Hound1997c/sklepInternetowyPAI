DROP FUNCTION IF EXISTS goldProduct;
DELIMITER $$
CREATE FUNCTION goldProduct()
RETURNS TEXT
DETERMINISTIC
BEGIN
	DECLARE v1 TEXT;
	DECLARE v2 TEXT;
		/*SET v1 :=
		(*/
		select p.name, sum(o.amount) as how_much_bought
		INTO @v1,@v2
		from product p left join orders o 
		on (o.product_id = p.id) 
		where o.state='accepted' 
		group by p.name
		order by how_much_bought desc
		LIMIT 1
		/*)*/
		;
	RETURN CONCAT(@v1," ",@v2);
END$$
DELIMITER ;

select p.name, sum(o.amount) as how_much_bought
		from product p left join orders o 
		on (o.product_id = p.id) 
		where o.state='accepted' 
		group by p.name
		order by how_much_bought desc
		/*LIMIT 1*/
		;

/*
SET result1 := 
		select p.name, sum(o.amount) as how_much_bought
		from product p left join orders o 
		on (o.product_id = p.id) 
		where o.state='accepted' group by p.name
		order by how_much_bought DESC
		LIMIT 1
		;
*/