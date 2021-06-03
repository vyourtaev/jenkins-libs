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
    mainEnvEnv = [:]
    ansibleHandler = ansibleObj
}

def getMainEnv() {
    return mainEnvdEnv
}

def test() {
    return ansibleHandler.play("Test case")
}
