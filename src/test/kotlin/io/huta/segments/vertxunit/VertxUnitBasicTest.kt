package io.huta.segments.vertxunit

import io.vertx.ext.unit.TestOptions
import io.vertx.ext.unit.TestSuite
import io.vertx.ext.unit.report.ReportOptions
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/*
    Introduction to testing with vertx-unit from https://vertx.io/docs/vertx-unit/java/
 */
class VertxUnitBasicTest {

    @Test
    fun first() {
        val suite = TestSuite.create("first")
        suite.test("first_test_case") { ctx ->
            val s = "value"
            ctx.assertEquals("value", s)
        }
        suite.run()
    }

    @Test
    fun second() {
        val suite = TestSuite.create("second")
        suite.test("second_test_case") { ctx ->
            val s = "value"
            ctx.assertEquals("value", s)
        }
        suite.run(TestOptions().addReporter(ReportOptions().setTo("console")))
    }

    @Test
    fun testSuiteOne() {
        val suite = TestSuite.create("test_suite_one")
        suite.test("test_case_1") {
            it.assertEquals(1, 1)
        }.test("test_case_2") {
            it.assertEquals(2, 2)
        }.test("test_case_3") {
            it.assertEquals(3, 3)
        }.run(TestOptions().addReporter(ReportOptions().setTo("console")))
    }

    @Test
    fun testSuiteTwo() {
        TestSuite.create("test suite two")
            .before { _ ->
                println("before")
            }.test("test_case_1") { ctx ->
                ctx.assertEquals(1, 1)
            }.test("test_case_2") { ctx ->
                ctx.assertEquals(2, 2)
            }.test("test_case_3") { ctx ->
                ctx.assertEquals(3, 3)
            }.after { _ ->
                println("after")
            }.run(TestOptions().addReporter(ReportOptions().setTo("console")))
    }

    @Test
    fun testSuiteThree() {
        TestSuite.create("test_suite_each")
            .beforeEach { println("before each") }
            .before { println("beforeAll") }
            .test("test_case_1") { ctx -> ctx.assertEquals(1, 1) }
            .test("test_case_2") { ctx -> ctx.assertEquals(2, 2) }
            .after { println("after") }
            .afterEach { println("after each") }
            .run(TestOptions().addReporter(ReportOptions().setTo("console")))
    }

    @Test
    fun assertThirdParty() {
        TestSuite.create("test_suite_assert_third_party")
            .test("third_party") { ctx ->
                ctx.verify { Assertions.assertEquals(1, 1) }
            }
    }
}
