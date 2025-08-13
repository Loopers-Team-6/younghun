explain SELECT
            p.id,
            p.brand_id,
            b.name,
            p.name,
            p.price,
            p.description,
            p.like_count,
            p.created_at,
            p.updated_at
        FROM product p
                 inner join brand b
                            on p.brand_id = b.id
                 inner join stock s
                            on p.id = s.product_id
        where p.brand_id = 1
        order by p.like_count desc
        limit 0,10;
