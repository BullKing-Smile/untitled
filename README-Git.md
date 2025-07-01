# Git


## Common Commands


## Issues

#### Git仓库指定使用那个ssh key
1. 查看配置
> git config --local core.sshCommand
> 结果为：ssh -i ~/.ssh/github_deploy_key

2. github 配置public key


3. 指定使用那个ssh key
> git config --local core.sshCommand "ssh -i ~/.ssh/github_deploy_key -o IdentitiesOnly=yes"

参数说明：
IdentitiesOnly=yes 告诉SSH只使用指定的密钥，而不是尝试所有可用密钥


全局解决方案，另外一种解决方案
> git config --global core.sshCommand "ssh -i ~/.ssh/your_key_file -o IdentitiesOnly=yes"