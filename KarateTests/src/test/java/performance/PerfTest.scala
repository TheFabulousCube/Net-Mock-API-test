package performance

import com.intuit.karate.gatling.PreDef._
import io.gatling.core.Predef._


class PerfTest extends Simulation {
val protocol = karateProtocol()

val listProducts = scenario("List all products").exec(karateFeature("classpath:performance/listProducts.feature"))
val catFact = scenario("Get some cat facts").exec(karateFeature("classpath:performance/catFact.feature"))

setUp(
listProducts.inject(atOnceUsers(10),
                rampUsers(10).during(10),
                constantUsersPerSec(10).during(10),
                rampUsersPerSec(3).to(7).during(10)).protocols(protocol),
catFact.inject(atOnceUsers(10),
                rampUsers(10).during(10),
                constantUsersPerSec(10).during(10),
                rampUsersPerSec(3).to(7).during(10)).protocols(protocol)
)
}