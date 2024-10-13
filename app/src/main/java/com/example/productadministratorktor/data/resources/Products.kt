package com.example.productadministratorktor.data.resources

import io.ktor.resources.Resource

@Resource("/products")
class Products() {
    // endpoint /products/:id
    @Resource("{id}")
    class Id(val parent: Products = Products(), val id: Int) {}
}