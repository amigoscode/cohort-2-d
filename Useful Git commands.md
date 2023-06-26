# Commands

## Create new branch

**NEW BRANCH**

```bash
$ git checkout -b ＜new-branch-name＞
```

Here we can create a new branch from an already existing branch, first we set the name of the new branch, then we add the name of the branch that we want to branch off .

```bash
$ git checkout -b <10*name-of-new-branch> <101-development>
```

If you want to change to another branch.

```bash
# Check available branches
$ git branch
# change to desired branch
$ git checkout <name-of-branch>
```

**DELETE A BRANCH**

```bash
$ git branch -d <branch-name>
```

**PUSH BRANCH TO REMOTE**

Once created, push to remote so it is not only in your local git.

```bash
$ git push <origin> <10*name-of-branch>
```

**SET UPSTREAM TO NEW BRANCH**

This is useful so we can just use `git push` instead of `git push origin <10...>`

```bash
$ git push origin -u <10*name-of-branch>
```

## Rename Branches

**RENAME LOCAL BRANCH**

```bash
$ git branch -m <old-branch-name> <new-branch-name>
```

Once the branch has been renamed, we will need to delete the old named branch from remote and push the newly named to origin.

**RENAME REMOTE BRANCH STEPS**

First check actual name

```bash
$ git branch -a
```

Delete branch with old name in remote

```bash
$ git push origin --delete <old-branch-name>
# To rename remote branch with one command
$ git push origin :<old-branch-name> <new-branch-name>
```

Create new branch name by push

```bash
$ git push origin -u <new-branch-name>
```

## Mergin to remote branch

**MERGE CHANGES TO 101-DEVELOPMENT WITHOUT FAST-FORWARD**

```bash
$ git checkout <101-development>
$ git merge --no-ff <10*name-of-branch>
```

**MERGE CHANGES**

```bash
# Merge changes into current branch from specific branch
$ git merge <branch-name>
```

**NOW PUSH CHANGES TO REMOTE**

```bash
$ git push origin <101-development>
$ git push origin <10*name-of-branch>
```

There is no need to `push` continuosly, only when the feature we are working on is cosidered complete or we specifically need the development branch to be updated with latest work. Obviously this can change if the feature needs changes in the future, but that should be fine becauase we just don't know.

## Pulling from remote

**PULL TO CURRENT BRANCH**

```bash
$ git pull
# If you want to be specific about which branch to pull from
$ git pull origin <name-of-branch>
# This is handy if you want to pull from 101-development, once a new
# merge has been done. Although it might be better to merge from
# development to your current branch, so it is updated with latest
# merge from development.
```

If we have uncommitted changes, git will ask us to either commit `git commit -m '...'`, or to `git stash` in order to pull. *(To retrieve stashed commits `$ git stash pop`)*

## Adding files to be commited/pushed

```bash
# We can add everything with
$ git add .
# We can add specific files by draggin into the terminal with mouse
$ git add <drag-file-to-terminal>
# Should see the whole file path
```

This can be useful only to commit one file, in case you have accidentaly touched or modified another file which someone might be dependant on for their own branches.
