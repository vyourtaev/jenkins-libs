def call(ansible, terraform){
    def mainEnv =  new com.callfire.main.Main()
    mainEnv.construct(ansible, terraform)
    return mainEnv
}