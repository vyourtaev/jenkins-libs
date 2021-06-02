def call(ansibleObj){
    def buildEnv =  new com.callfire.BuildEnv()
    buildEnv.construct(ansibleObj)
    return buildEnv
}
