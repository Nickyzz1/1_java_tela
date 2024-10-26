// import javax.persistence.EntityManager;
// import javax.persistence.EntityManagerFactory;
// import javax.persistence.Persistence;

// public class EntityManagerUtil {
//     private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("my-persistence-unit");

//     public static EntityManager getEntityManager() {
//         return entityManagerFactory.createEntityManager();
//     }

//     public static void closeEntityManagerFactory() {
//         entityManagerFactory.close();
//     }
// }

// create

// public void createProduct(Product product) {
//     EntityManager em = EntityManagerUtil.getEntityManager();
//     try {
//         em.getTransaction().begin();
//         em.persist(product); // Salvar o novo produto
//         em.getTransaction().commit();
//     } catch (Exception e) {
//         if (em.getTransaction().isActive()) {
//             em.getTransaction().rollback(); // Reverter em caso de erro
//         }
//         e.printStackTrace();
//     } finally {
//         em.close(); // Fechar o EntityManager
//     }
// }

// ler

// public Product getProduct(Long productId) {
//     EntityManager em = EntityManagerUtil.getEntityManager();
//     Product product = null;
//     try {
//         product = em.find(Product.class, productId); // Encontrar o produto pelo ID
//     } finally {
//         em.close(); // Fechar o EntityManager
//     }
//     return product;
// }


// atualizar

// public void updateProduct(Product product) {
//     EntityManager em = EntityManagerUtil.getEntityManager();
//     try {
//         em.getTransaction().begin();
//         em.merge(product); // Atualizar o produto existente
//         em.getTransaction().commit();
//     } catch (Exception e) {
//         if (em.getTransaction().isActive()) {
//             em.getTransaction().rollback(); // Reverter em caso de erro
//         }
//         e.printStackTrace();
//     } finally {
//         em.close(); // Fechar o EntityManager
//     }
// }


// delete

// public void deleteProduct(Long productId) {
//     EntityManager em = EntityManagerUtil.getEntityManager();
//     try {
//         em.getTransaction().begin();
//         Product product = em.find(Product.class, productId); // Encontrar o produto
//         if (product != null) {
//             em.remove(product); // Remover o produto
//         }
//         em.getTransaction().commit();
//     } catch (Exception e) {
//         if (em.getTransaction().isActive()) {
//             em.getTransaction().rollback(); // Reverter em caso de erro
//         }
//         e.printStackTrace();
//     } finally {
//         em.close(); // Fechar o EntityManager
//     }
// }

//uso

// public static void main(String[] args) {
//     Product product = new Product();
//     product.setNameProd("Produto 1");
//     product.setPriceProd(99.99);

//     // Criar um novo produto
//     createProduct(product);

//     // Ler um produto
//     Product retrievedProduct = getProduct(product.getId());
//     System.out.println("Produto lido: " + retrievedProduct.getNameProd());

//     // Atualizar o produto
//     retrievedProduct.setPriceProd(79.99);
//     updateProduct(retrievedProduct);

//     // Deletar o produto
//     deleteProduct(retrievedProduct.getId());

//     // Fechar o EntityManagerFactory ao final
//     EntityManagerUtil.closeEntityManagerFactory();
// }


