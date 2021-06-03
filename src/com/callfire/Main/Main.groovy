package com.callfire.Main

mainEnv

ansibleHandler

/**
 * Imitates a constructor with object composition
 * Defines an instance of mainEnv object.
 * @param ansibleObj - an Ansible object
 * @return
 */
def construct(ansibleObj){
    mainEnv = [1:"one"]
    ansibleHandler = ansibleObj
}

def getMainEnv() {
    return mainEnv
}

def custom1(String args) {
    return ansibleHandler.play(args)
}
