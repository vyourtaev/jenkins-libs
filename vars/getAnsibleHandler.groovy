def call(String args){
    def ansible =  new com.callfire.ansible.Ansible()
    ansible.construct(args)
    return ansible
}