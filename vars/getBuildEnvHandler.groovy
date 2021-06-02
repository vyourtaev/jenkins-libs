def call(ansibleObj){
    def env =  new com.callfire.BuildEnv()
    env.construct(ansibleObj)
    return env
}
