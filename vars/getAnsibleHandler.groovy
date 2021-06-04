def call(piplineParams){
    def ansible =  new com.callfire.ansible.Ansible()
    ansible.construct(piplineParams)
    return ansible
}