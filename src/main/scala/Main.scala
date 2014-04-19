package libraryDependencies

import javax.servlet.http._
import java.net.URL
import scala.xml.{Elem, Node, XML}

object Main {
  final val doctype = """<!DOCTYPE svg PUBLIC "-//W3C//DTD SVG 1.0//EN" "http://www.w3.org/TR/2000/CR-SVG-20001102/DTD/svg-20001102.dtd">"""

  def createSVG(org: String, name: String): xml.Elem = {
    val width = "700"
    val height = 50
    val viewBox = Seq(-10, -10, width, height).mkString(" ")
    <svg id="body" width={width} height={height.toString} viewBox={viewBox} xmlns:xlink="http://www.w3.org/1999/xlink" xmlns="http://www.w3.org/2000/svg">
      <rect x="1" y="1" width={width} height={(height - 4).toString} fill="#EEEEEE" stroke="black" stroke-width="0">
      </rect>
      <text x="5" y={(height / 2).toString} font-family="monospace" font-size="16">"{org}" % "{name}" % "{latestVersion(org, name)}"</text>
    </svg>
  }

  def latestVersion(org: String, name: String): String = {
    val url = "http://repo1.maven.org/maven2/" + org.replace('.', '/') + "/" + name + "/maven-metadata.xml"
    (scala.xml.XML.load(new URL(url)) \\ "latest").text
  }
}

final class Main extends HttpServlet {

  override def doGet(req: HttpServletRequest, resp: HttpServletResponse) {
    val writer = resp.getWriter
    try{
      val org = req.getParameter("org")
      val name = req.getParameter("name")
      writer.println(
        Main.doctype + Main.createSVG(org, name).toString
      )
      resp.setContentType("image/svg+xml")
      resp.setHeader("Pragma", "no-cache")
      resp.setHeader("Cache-Control", "no-cache")
      resp.setDateHeader("Expires", 0)
    } catch {
      case e: Throwable =>
        writer.println(e.toString)
        e.getStackTrace.foreach{ stackTrace =>
          writer.print("\tat ")
          writer.println(stackTrace)
        }
    }
  }

}
