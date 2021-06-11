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
def construct(ansible, terraform) {
    mainEnv = [
            JENKINS_HOME: '/var/lib/jenkins',
            vault_token_path: '/etc/vault/agent/token/vault-token'

    ]
    ansibleHandler = ansible
    terraformHandler = terraform

    env.VAULT_TOKEN = new File(mainEnv.vault_token_path).text
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
def terraform(args){
    return terraformHandler.plan("printenv")
}

/**
 * Register our data
 * @param args - json as string
 * @return stdout
 */
def provision(Map args){
    return [
       terraformHandler.workspace_list()
//       terraformHandler.workspace_init()
//       terraformHandler.apply(args)
    ]
}

/**
 * Register our data
 * @param args - json as string
 * @return stdout
 */
def destroy(Map args){
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

/**
 * Get lists of envrionment
 * * @param args - json as string
 * @return stdout
 */
def printenv(args = ''){
    return terraformHandler.getEnvironmentVariables()
//    return sh (script: "echo $env.WORKSPACE")
}