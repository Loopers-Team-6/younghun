CREATE INDEX idx_brand_id_and_id ON product (brand_id, id);
CREATE INDEX idx_product_id_and_like_count ON product_status (product_id, like_count DESC);
