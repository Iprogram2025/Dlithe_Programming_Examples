'''Write a Python program that finds the longest palindromic substring in a given string using dynamic programming.

Pseudocode for Finding Longest Palindromic Substring
BEGIN
    FUNCTION LONGEST_PALINDROMIC_SUBSTRING(S):
        IF S is empty:
            RETURN ""

        Initialize DP table of size NxN (N = length of S)
        Set start_index and max_length
        
        FOR each character in S:
            Mark single-character palindromes
            
        FOR two-character substrings:
            Mark palindromes if both characters match
        
        FOR substring lengths 3 to N:
            FOR each start index:
                Compute end index
                Check if substring is palindrome (using DP table)
                
                IF palindrome found:
                    Update start_index and max_length
        
        RETURN longest substring from S[start_index : start_index + max_length]
    
    MAIN():
        Take user input string
        Print longest palindromic substring using function
END

Input :
babad

Expected Output
Longest Palindromic Substring: aba

(Another valid output: "bab" since both are correct answers.)
Explanation:
- The longest palindromic substring found in "babad" is "aba" or "bab", both of length 3.


'''
def longest_palindromic_substring(s):
    if not s:
        return ""
    
    n = len(s)
    dp = [[False] * n for _ in range(n)]
    start, max_length = 0, 1

    # Every single character is a palindrome
    for i in range(n):
        dp[i][i] = True
    
    # Check for two-character palindromes
    for i in range(n - 1):
        if s[i] == s[i + 1]:
            dp[i][i + 1] = True
            start, max_length = i, 2

    # Check for palindromes of length 3 and more
    for length in range(3, n + 1):
        for i in range(n - length + 1):
            j = i + length - 1
            if s[i] == s[j] and dp[i + 1][j - 1]:
                dp[i][j] = True
                start, max_length = i, length

    return s[start:start + max_length]

# Example usage
string = "babad"
print("Longest Palindromic Substring:", longest_palindromic_substring(string))