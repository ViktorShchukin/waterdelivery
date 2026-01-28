CREATE TABLE basket (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    CONSTRAINT fk_basket_user FOREIGN KEY (user_id) REFERENCES app_user(id) ON DELETE CASCADE
);

CREATE TABLE basket_item (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    basket_id UUID NOT NULL,
    product_id UUID NOT NULL,
    quantity INT NOT NULL DEFAULT 1,

    CONSTRAINT fk_basket_item_basket FOREIGN KEY (basket_id) REFERENCES basket(id) ON DELETE CASCADE,
    CONSTRAINT fk_basket_item_product FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE RESTRICT,
    CONSTRAINT uk_basket_product UNIQUE (basket_id, product_id)
);