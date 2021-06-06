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
        vault_token_path: '/etc/vault/agent/token/vault-token'

    ]
    ansibleHandler = ansible
    terraformHandler = terraform

    env.VAULT_TOKEN = readFile(mainEnv.vault_token_path)
}

def getMainEnv() {
    return mainEnv
}

/**
 * Register our data
 * @param args - json as string
 * @return stdout
 */
def test(args) {
    return ansibleHandler.play(args)
}

/**
 * Register our data
 * @param args - json as string
 * @return stdout
 */
def plan(args){
    return terraformHandler.plan(args)
}

/**
 * Register our data
 * @param args - json as string
 * @return stdout
 */
def provision(String args){
    return terraformHandler.apply(args)
}

/**
 * Register our data
 * @param args - json as string
 * @return stdout
 */
def destroy(String args){
    return terraformHandler.destroy(args)
}

/**
 * Register our data
 * @param args - json as string
 * @return stdout
 */
def deploy(String args){
    return ansibleHandler.playbook(args)
}
