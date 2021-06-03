def call(ansible){
    def mainEnv =  new com.callfire.main.Main()
    mainEnv.construct(ansible)
    return mainEnv
}
