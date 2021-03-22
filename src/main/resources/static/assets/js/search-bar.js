const productsList = document.getElementById('productsList')
const searchBar = document.getElementById('searchBar')

const allProducts = [];

fetch("http://localhost:8080/shop/api").then(response => response.json()).then(data => {
    for (let product of data) {
        allProducts.push(product);
    }
})

console.log(allProducts);

searchBar.addEventListener('keyup', (e) => {
    const searchingCharacters = searchBar.value.toLowerCase();
    let filteredProducts = allProducts.filter(product => {
        return product.name.toLowerCase().includes(searchingCharacters);
    });
    displayAlbums(filteredProducts);
})


const displayAlbums = (products) => {
    productsList.innerHTML = products
        .map((p) => {
            console.log(p.src);
            return ` <div class="tab-pane fade show active" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab">
                    <div class="row">
                        <div 
                              class="col-xl-4 col-lg-4 col-md-6 col-sm-6">
                            <div class="single-popular-items mb-50 text-center">
                                <div class="popular-img">
                                   <img src="${p.imageUrls[0]}" width="361" height="380"
                                         alt="">
                                    <div class="img-cap">
                                        <a href="/add-to-cart/${p.id}"><span>Add to cart</span></a>
                                        <a sec:authorize="hasAuthority('ADMIN')"
                                           href="@{/product/edit/{id}(id = *{id})}"><span>Edit Product</span></a>
                                        <a sec:authorize="hasAuthority('ADMIN')"
                                           href="@{/product/delete/{id}(id = *{id})}"><span>Delete Product</span></a>
                                    </div>
                                    <div class="favorit-items">
                                        <span class="flaticon-heart"></span>
                                    </div>
                                </div>
                                <div class="popular-caption">
                                    <h3><a href="/product/details/${p.id}">${p.name}</a>
                                    </h3>
                                    <span>${p.price}</span>
                                </div>
                            </div>
                        </div>
                    </div></div>

                
                 `
        })
        .join('');

}