package controllers

import models.Product
import play.api.Configuration
import play.api.i18n.I18nSupport
import play.api.mvc._

import javax.inject._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class ProductsController @Inject()(cc: ControllerComponents, config: Configuration) extends AbstractController(cc) with I18nSupport {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def listOfProducts() = Action { implicit request: Request[AnyContent] =>
    val products = Product.findAll
    val messages = messagesApi.preferred(request)


    Ok(views.html.products.list(products, config)(messages.lang, messages))
  }


  def show(ean: Long) = Action { implicit request =>

    val messages = messagesApi.preferred(request)
    Product.findByEan(ean).map { product =>
      Ok(views.html.products.details(product, config)(messages.lang, messages))
    }.getOrElse(NotFound)
  }
}
