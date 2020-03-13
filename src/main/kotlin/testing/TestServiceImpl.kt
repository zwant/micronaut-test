package testing

import javax.inject.Singleton

@Singleton
internal class TestServiceImpl : TestService {
    override fun doSomething(theThing: String): MyResponse {
        return MyResponse(theThing)
    }
}
