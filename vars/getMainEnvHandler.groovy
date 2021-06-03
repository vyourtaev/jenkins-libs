def call(ansibleObj){
    def mainEnv =  new com.callfire.BuildEnv()
    mainEnvEnv.construct(ansibleObj)
    return mainEnvEnv
}
