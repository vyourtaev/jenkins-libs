package com.callfire.main

mainEnv

ansibleHandler
terraformHandler

/**
 * Imitates a constructor with object composition
 * Defines an instance of mainEnv object.
 * @param ansibleObj - an Ansible object
 * @return
 */
def construct(ansible, terraform){
    mainEnv = [
            1:  "one",
            2:  "two",
            3:  "three"
    ]
    ansibleHandler = ansible
    terraformHandler = terraform
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

def provision(String args){
    return terraformHandler.apply(args)
}

def destroy(String args){
    return terraformHandler.destroy(args)
}
