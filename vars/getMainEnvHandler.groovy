def call(ansibleObj){
    def mainEnv =  new com.callfire.BuildEnv()
    mainEnv.construct(ansibleObj)
    return mainEnv
}
