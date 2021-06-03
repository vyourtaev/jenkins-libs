package com.callfire.main

mainEnv

ansibleHandler

/**
 * Imitates a constructor with object composition
 * Defines an instance of mainEnv object.
 * @param ansibleObj - an Ansible object
 * @return
 */
def construct(ansibleObj){
    mainEnv = [
            1:  "one",
            2:  "two",
            3:  "three"
    ]
    ansibleHandler = ansibleObj
}

def getMainEnv() {
    return mainEnv
}

/**
 * Register our data
 * @param args - json as string
 * @return stdout
 */
def test(String args) {
    return ansibleHandler.play(args)
}
